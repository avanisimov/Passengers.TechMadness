import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'repositories.dart';

import 'models.dart';

void main() {
  Repository repository = Repository();
  runApp(SampleApp(repository));
}

class SampleApp extends StatelessWidget {
  final Repository repository;

  SampleApp(this.repository);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Sample App',
        theme: ThemeData(
          primarySwatch: Colors.red,
        ),
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
                      create: (_) => RegionsModel(repository))
                ],
              )
        });
  }
}

class AudienceListModel with ChangeNotifier {
  List<AudienceShort> audience = [];

  AudienceListModel(Repository repository) {
    fetchData(repository);
  }

  void fetchData(Repository repository) async {
    audience =
        await repository.getAudiences().then((response) => response.items);
    notifyListeners();
  }
}

class AudienceListPage extends StatelessWidget {
  final Repository repository;

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
    return Card(
      child: InkWell(
        splashColor: Colors.blue.withAlpha(30),
        onTap: () {
          Navigator.pushNamed(context, '/article-details');
        },
        child: Container(
          child: Padding(
              padding: EdgeInsets.all(16),
              child: Column(children: [
                Text("${item.title}",
                    style:
                        TextStyle(fontSize: 20, fontWeight: FontWeight.w600)),
                Text(item.description,
                    maxLines: null,
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w300))
              ])),
        ),
      ),
    );
  }
}

class AudienceDetailsPage extends StatelessWidget {
  final Repository repository;

  AudienceDetailsPage(this.repository);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Tech.Madness")),
      body: Padding(
        padding: EdgeInsets.all(24),
        child: Column(
          children: <Widget>[
            IncomeRangeStateCard(),
            RegionCard(),
          ],
        ),
      ),
    );
  }
}

class ParameterCard extends StatelessWidget {
  final String title;
  final List<Widget> children;

  ParameterCard({String title, List<Widget> children})
      : this.title = title,
        this.children = children;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 8,
      child: Container(
        padding: EdgeInsets.all(16),
        child: Column(
          children: <Widget>[
            Container(
              alignment: Alignment(-1, 0),
              child: Text(
                title,
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
              ),
            ),
            ...children,
          ],
        ),
      ),
      margin: EdgeInsets.all(16),
    );
  }
}

class IncomeRangeStateCard extends StatelessWidget {
  IncomeRangeStateCard();

  @override
  Widget build(BuildContext context) {
    final config = Provider.of<IncomeModel>(context);
    print("Income card config=${config}");
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

  Row createPresetsBar(BuildContext context, IncomeModel rangeConfig) {
    double opacity = 0.1;
    List<Widget> presets = rangeConfig.rangePresets
        .map((preset) => GestureDetector(
            onTap: () {
              rangeConfig.range = preset.range;
            },
            child: Container(
              child: Text(preset.title),
              padding: EdgeInsets.all(16),
              alignment: Alignment(0.0, 0.0),
              width: 100,
              color: Color.fromRGBO(255, 100, 0, opacity += 0.1),
            )))
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
        SizedBox(
          height: 400,
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

  IncomeModel(Repository repository) {
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

  void fetchData(Repository repository) async {
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

  RegionsModel(Repository repository) {
    fetchData(repository);
  }

  RegionItem getItem(int index) => _regions[index];

  void fetchData(Repository repository) async {
    _regions = await repository
        .getRegions()
        .then((list) => list.map((item) => RegionItem(item)).toList());
    notifyListeners();
  }
}

