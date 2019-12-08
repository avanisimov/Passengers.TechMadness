import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'models.dart';
import 'repositories.dart';
import 'widgets.dart';
import 'widgets.dart';

void main() {
  AudienceRepository repository = AudienceRepository();
  runApp(App(repository));
}

class App extends StatelessWidget {
  final AudienceRepository repository;

  App(this.repository);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Sample App',
        theme: ThemeData(
            primarySwatch: Colors.blue, accentColor: Colors.blueAccent),
        home: ChangeNotifierProvider.value(
          value: AudienceListModel(repository),
          child: AudienceListPage(repository),
        ),
        routes: <String, WidgetBuilder>{
          '/article-details': (BuildContext context) => MultiProvider(
                child: AudienceDetailsPage(repository),
                providers: [
                  ChangeNotifierProvider(
                      create: (_) => IncomeModel(repository)),
                  ChangeNotifierProvider(
                      create: (_) => RegionsModel(repository)),
                  ChangeNotifierProvider(
                      create: (_) => RegistrationPeriodModel()),
                  ChangeNotifierProvider(
                      create: (_) => TransactionCountFilterModel())
                ],
              )
        });
  }
}

class AudienceListModel with ChangeNotifier {
  List<AudienceShort> audience = [];

  AudienceListModel(AudienceRepository repository) {
    fetchData(repository);
  }

  void fetchData(AudienceRepository repository) async {
    audience =
        await repository.getAudiences().then((response) => response.items);
    notifyListeners();
  }
}

class AudienceListPage extends StatelessWidget {
  final AudienceRepository repository;

  AudienceListPage(this.repository);

  @override
  Widget build(BuildContext context) {
    AudienceListModel model = Provider.of<AudienceListModel>(context);
    return Scaffold(
        appBar: AppBar(
          title: Text("Sample App"),
        ),
        body: ListView.builder(
            itemCount: model.audience.length,
            itemBuilder: (BuildContext context, int position) {
              return getRow(context, model.audience[position]);
            }));
  }

  Widget getRow(BuildContext context, AudienceShort item) {
    return InkWell(
      splashColor: Colors.blue.withAlpha(30),
      onTap: () {
        Navigator.pushNamed(context, '/article-details');
      },
      child: Container(
        margin: EdgeInsets.fromLTRB(24, 4, 0, 4),
        child: ParameterCard(
          title: "${item.title}",
          children: [
            Container(
              alignment: Alignment(-1, 0),
              child: Text(item.description,
                  textAlign: TextAlign.start,
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w300)),
            )
          ],
        ),
      ),
    );
  }
}

class AudienceDetailsPage extends StatelessWidget {
  final AudienceRepository repository;

  AudienceDetailsPage(this.repository);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Tech.Madness"),
        actions: <Widget>[
          FlatButton(
              child: Text(
                "Save",
                style:
                    TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
              ),
              onPressed: () {})
        ],
      ),
      body: GridView.count(
        childAspectRatio: 2,
        mainAxisSpacing: 10,
        padding: EdgeInsets.fromLTRB(32, 8, 24, 0),
        children: <Widget>[
          ConstrainedBox(
            child: IncomeRangeStateCard(),
            constraints: BoxConstraints(maxHeight: 200),
          ),
          RegionCard(),
          RegistrationFilterCard(),
          TransactionFilterCard()
        ],
        crossAxisCount: 3,
      ),
    );
  }
}

class IncomeRangeStateCard extends StatelessWidget {
  IncomeRangeStateCard();

  @override
  Widget build(BuildContext context) {
    final config = Provider.of<IncomeModel>(context);
    return ParameterCard(
      title: "Income from company",
      children: <Widget>[
        RangeSlider(
          min: config.minValue,
          max: config.maxValue,
          values: config.range,
          onChanged: (RangeValues newRange) {
            config.range = newRange;
          },
          activeColor: Colors.red,
        ),
        createPresetsBar(context, config)
      ],
    );
  }

  Widget createPresetsBar(BuildContext context, IncomeModel rangeConfig) {
    double opacity = 0.1;
    List<Widget> presets = rangeConfig.rangePresets
        .map((preset) => Expanded(
              child: GestureDetector(
                  onTap: () {
                    rangeConfig.range = preset.range;
                  },
                  child: Container(
                    child: Text(preset.title),
                    padding: EdgeInsets.all(16),
                    alignment: Alignment(0.0, 0.0),
                    width: 100,
                    color: Color.fromRGBO(255, 100, 0, opacity += 0.05),
                  )),
            ))
        .toList();
    return Row(
      children: presets,
    );
  }
}

class RegionCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    RegionsModel regionsModel = Provider.of<RegionsModel>(context);
    return ParameterCard(
      title: "Regions",
      children: <Widget>[
        Divider(thickness: 1),
        Expanded(
          child: ListView.separated(
            shrinkWrap: true,
            itemCount: regionsModel.regions.length,
            itemBuilder: (context, index) =>
                getRow(regionsModel.getItem(index)),
            separatorBuilder: (context, index) => Divider(thickness: 1),
          ),
        )
      ],
    );
  }

  Widget getRow(RegionItem item) {
    return ChangeNotifierProvider(
      child: Consumer<RegionItemWrapper>(
          builder: (context, item, _) => Container(
                  // padding: Insets.fromLTRB(0, 4, 0, 4),
                  child: Row(
                children: <Widget>[
                  Checkbox(
                    onChanged: (checked) {
                      item.checked = checked;
                    },
                    value: item.isChecked,
                  ),
                  Text(item.title)
                ],
              ))),
      create: (BuildContext context) => RegionItemWrapper(item),
    );
  }
}

class RegionItem {
  final String title;
  bool _checked = false;

  RegionItem(this.title);

  bool get isChecked => _checked;

  set checked(bool checked) {
    _checked = checked;
  }
}

class RegionItemWrapper with ChangeNotifier {
  final RegionItem _item;
  RegionItemWrapper(this._item);
  bool get isChecked => _item.isChecked;
  set checked(bool checked) {
    _item.checked = checked;
    notifyListeners();
  }

  String get title => _item.title;
}

class IncomeModel with ChangeNotifier {
  RangeConfig _config = RangeConfig(0, 1, []);

  int _startValue = 0;
  int _endValue = 1;

  IncomeModel(AudienceRepository repository) {
    fetchData(repository);
  }

  double get minValue => _config.minValue.toDouble();

  double get maxValue => _config.maxValue.toDouble();

  List<RangePreset> get rangePresets => _config.presets;

  set startValue(int startValue) {
    _startValue = startValue;
    notifyListeners();
  }

  get startValue {
    if (_startValue >= _config.minValue && _startValue <= _config.maxValue) {
      print("startValue=$_startValue");
      return _startValue;
    } else {
      print("endValue=${_config.minValue}");
      return _config.minValue;
    }
  }

  set endValue(int endValue) {
    _endValue = endValue;
    notifyListeners();
  }

  get endValue {
    if (_endValue >= _config.minValue && _endValue <= _config.maxValue) {
      print("endValue=$_endValue");
      return _endValue;
    } else {
      print("endValue=${_config.maxValue}");
      return _config.maxValue;
    }
  }

  get range => RangeValues(_startValue.toDouble(), _endValue.toDouble());

  set range(RangeValues range) {
    _startValue = range.start.toInt();
    _endValue = range.end.toInt();
    notifyListeners();
  }

  void fetchData(AudienceRepository repository) async {
    _config = await repository.getLtvConfigAsync();
    _startValue = _config.minValue;
    _endValue = _config.minValue;
    notifyListeners();
  }
}

class RegionsModel with ChangeNotifier {
  List<RegionItem> _regions = [];

  List<RegionItem> get regions => _regions;

  List<String> get selectedRegions =>
      _regions.where((item) => item.isChecked).map((item) => item.title);

  RegionsModel(AudienceRepository repository) {
    fetchData(repository);
  }

  RegionItem getItem(int index) => _regions[index];

  void fetchData(AudienceRepository repository) async {
    _regions = await repository
        .getRegions()
        .then((list) => list.map((item) => RegionItem(item)).toList());
    notifyListeners();
  }
}

class RegistrationFilterCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    RegistrationPeriodModel model =
        Provider.of<RegistrationPeriodModel>(context);
    return ParameterCard(
      title: "Registration date",
      children: <Widget>[
        Row(
          children: getChips(model),
        )
      ],
    );
  }

  List<Widget> getChips(RegistrationPeriodModel model) {
    return model.periods
        .map((period) => Container(
            padding: EdgeInsets.fromLTRB(0, 8, 8, 8),
            child: ChoiceChip(
              label: Text("${period.count} month"),
              selected: period.selected,
              onSelected: (selected) {
                model.updatePeriod(period, selected);
              },
            )))
        .toList();
  }
}

class MonthPeriod {
  final int count;
  bool selected = false;
  MonthPeriod(this.count);
}

class RegistrationPeriodModel with ChangeNotifier {
  List<MonthPeriod> periods = List.unmodifiable(
      [MonthPeriod(1), MonthPeriod(3), MonthPeriod(6), MonthPeriod(12)]);

  RegistrationPeriodModel() {
    print("RegistrationPeriodModel is created");
  }

  void updatePeriod(MonthPeriod period, bool selected) {
    period.selected = selected;
    notifyListeners();
  }
}

class TransactionFilterCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final model = Provider.of<TransactionCountFilterModel>(context);
    return ParameterCard(
      title: "Transactions amount",
      children: <Widget>[
        Slider(
          onChanged: (double value) {
            model.selectedValue = value.toInt();
          },
          value: model.selectedValue.toDouble(),
          min: model.minValue.toDouble(),
          max: model.maxValue.toDouble(),
        ),
        Container(
          child: Text("Selected: ${model.selectedValue}"),
          alignment: Alignment(1, 0),
        )
      ],
    );
  }
}

class TransactionCountFilterModel with ChangeNotifier {
  final int maxValue;
  final int minValue;
  int _selectedValue = 0;

  TransactionCountFilterModel({int minValue = 0, int maxValue = 1000000})
      : this.maxValue = maxValue,
        this.minValue = minValue,
        this._selectedValue = minValue;

  int get selectedValue => _selectedValue;

  set selectedValue(int newValue) {
    if (newValue < minValue) {
      _selectedValue = minValue;
    } else if (newValue > maxValue) {
      _selectedValue = maxValue;
    } else {
      _selectedValue = newValue;
    }
    notifyListeners();
  }
}
