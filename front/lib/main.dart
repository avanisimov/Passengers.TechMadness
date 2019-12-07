import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';

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
          '/article-details': (BuildContext context) =>
              ChangeNotifierProvider.value(
                child: AudienceDetailsPage(repository),
                value: RangeConfigNotifier(repository),
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

class AudienceListPage extends StatefulWidget {
  final Repository repository;

  AudienceListPage(this.repository);

  @override
  _AudienceListPageState createState() => _AudienceListPageState(repository);
}

class _AudienceListPageState extends State<AudienceListPage> {
  List<AudienceShort> widgets = [];

  final Repository repository;

  _AudienceListPageState(this.repository);

  @override
  void initState() {
    super.initState();

    loadData();
  }

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
              return getRow(position, model.audience);
            }));
  }

  Widget getRow(int i, List<AudienceShort> audience) {
    final AudienceShort item = audience[i];
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

  loadData() async {
    AudienceShortListResponse listResponse = await repository.getAudiences();
    print(listResponse);
    setState(() {
      widgets = listResponse.items;
    });
  }
}

class AudienceDetailsPage extends StatelessWidget {
  final Repository repository;

  AudienceDetailsPage(this.repository);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Creation of app")),
      body: Padding(
        padding: EdgeInsets.all(24),
        child: Column(
          children: <Widget>[
            ChangeNotifierProvider.value(
              child: Consumer<RangeConfigNotifier>(
                builder: (context, value, _) => IncomeRangeStateCard(value),
              ),
              value: RangeConfigNotifier(repository),
            )
          ],
        ),
      ),
    );
  }
}

class IncomeRangeStateCard extends StatelessWidget {
  final RangeConfigNotifier rangeState;

  IncomeRangeStateCard(this.rangeState);

  @override
  Widget build(BuildContext context) {
    final config = Provider.of<RangeConfigNotifier>(context);
    print("rangeState is $config");
    return Card(
      elevation: 8,
      child: Container(
        padding: EdgeInsets.all(16),
        child: Column(
          children: <Widget>[
            Text(
              "Income from company",
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
            ),
            Container(
              child: RangeSlider(
                min: config.minValue,
                max: config.maxValue,
                values: config.range,
                onChanged: (RangeValues newRange) {
                  config.range = newRange;
                },
                activeColor: Colors.red,
              ),
            ),
            createPresetsBar(context, config)
          ],
        ),
      ),
      margin: EdgeInsets.all(16),
    );
  }

  Row createPresetsBar(BuildContext context, RangeConfigNotifier rangeConfig) {
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

class RangeConfig {
  final int minValue;
  final int maxValue;
  final List<RangePreset> presets;

  RangeConfig(this.minValue, this.maxValue, this.presets);
}

class RangePreset {
  final String title;
  final int startValue;
  final int endValue;

  RangePreset(this.title, this.startValue, this.endValue);

  RangeValues get range => RangeValues(startValue.toDouble(), endValue.toDouble());
}

class RangeConfigNotifier with ChangeNotifier {
  RangeConfig _config = RangeConfig(0, 1, []);

  int _startValue = 0;
  int _endValue = 1;

  RangeConfigNotifier(Repository repository) {
    // fetchData(repository);
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

class Repository {
  static String baseUrl = "http://167.71.48.207:9999/api/web";

  Future<AudienceShortListResponse> getAudiences() async {
    String dataURL = "$baseUrl/audiences?skip=0&take=20";
    print("Start request for audience list url=$dataURL");
    return Future.value(AudienceShortListResponse(
        20, [AudienceShort("id", "title", "description")]));

    // return http
    //     .get("http://167.71.48.207:9999/api/web/audiences?skip=0&take=20")
    //     .then((value) =>
    //         AudienceShortListResponse.fromJson(json.decode(value.body)));
  }

  Future<RangeConfig> getLtvConfigAsync() async {
    return Future.value(RangeConfig(0, 300000, [
      RangePreset("100", 1500, 10000),
      RangePreset("200", 15000, 20000),
      RangePreset("300", 25000, 30000),
      RangePreset("400", 40000, 60000),
      RangePreset("500", 75000, 83000),
      RangePreset("600", 83000, 100000),
      RangePreset("700", 120000, 200000),
    ]));
  }

  Future<List<String>> getCities() async {
    return Future.value([
      "Адыгея",
      "Алтай",
      "Алтайский",
      "Амурская",
      "Архангельская",
      "Астраханская",
      "Башкортостан",
      "Белгородская",
      "Брянская",
      "Бурятия",
      "Владимирская",
      "Волгоградская",
      "Вологодская",
      "Воронежская",
      "Дагестан",
      "Еврейская",
      "Забайкальский",
      "Ивановская",
      "Ингушетия",
      "Иркутская",
      "Кабардино-Балкарская",
      "Калининградская",
      "Калмыкия",
      "Калужская",
      "Камчатский",
      "Карачаево-Черкесская",
      "Карелия",
      "Кемеровская",
      "Кировская",
      "Коми",
      "Костромская",
      "Краснодарский",
      "Красноярский",
      "Крым",
      "Курганская",
      "Курская",
      "Ленинградская",
      "Липецкая",
      "Магаданская",
      "Марий Эл",
      "Мордовия",
      "Москва",
      "Московская",
      "Мурманская",
      "Ненецкий",
      "Неопределено",
      "Нижегородская",
      "Новгородская",
      "Новосибирская",
      "Омская",
      "Оренбургская",
      "Орловская",
      "Пензенская",
      "Пермский",
      "Приморский",
      "Псковская",
      "Ростовская",
      "Рязанская",
      "Самарская",
      "Санкт-Петербург",
      "Саратовская",
      "Саха (Якутия)",
      "Сахалинская",
      "Свердловская",
      "Севастополь",
      "Северная Осетия - Алания",
      "Смоленская",
      "Ставропольский",
      "Тамбовская",
      "Татарстан",
      "Тверская",
      "Томская",
      "Тульская",
      "Тыва",
      "Тюменская",
      "Удмуртская",
      "Ульяновская",
      "Хабаровский",
      "Хакасия",
      "Ханты-Мансийский",
      "Челябинская",
      "Чеченская",
      "Чувашская",
      "Чукотский",
      "Ямало-Ненецкий",
      "Ярославская"
    ]);
  }
}
