import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'models.dart';
import 'repositories.dart';
import 'widgets.dart';
import 'routing.dart';

class AudienceListPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    AudienceListModel model = Provider.of<AudienceListModel>(context);
    return Scaffold(
        appBar: AppBar(
          title: Text("Tech.Madness"),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              Expanded(
                child: ListView.builder(
                    itemCount: model.audience.length,
                    itemBuilder: (BuildContext context, int position) {
                      return getRow(context, model.audience[position]);
                    }),
              ),
              Container(
                padding: EdgeInsets.all(8),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: <Widget>[
                    MaterialButton(
                      child: Text(
                        "ADD AUDIENCE",
                        style: TextStyle(color: Colors.black87),
                      ),
                      color: Colors.white70,
                      onPressed: () { Navigator.pushNamed(context , '/article-details', arguments: AudienceArg.empty());},
                    ),
                    Padding(padding: EdgeInsets.all(4)),
                    MaterialButton(
                      child: Text(
                        "CREATE COMPAGN",
                        style: TextStyle(color: Colors.white),
                      ),
                      color: Colors.blue,
                      onPressed: () {},
                    ),
                  ],
                ),
              )
            ],
          ),
        ));
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