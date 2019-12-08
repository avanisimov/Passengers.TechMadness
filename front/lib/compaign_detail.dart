import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'models.dart';
import 'repositories.dart';
import 'widgets.dart';
import 'routing.dart';

class EditCompaignPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Tech.Madness")),
      body: Center(
        heightFactor: 1,
        widthFactor: 1,
        child: ListView(children: <Widget>[
          // place widgets here
        ]),
      ),
    );
  }
}

class EditCompaignModel with ChangeNotifier {
  String title;
  String summary;
  String fullDescription;
  List<String> channels;
  List<String> audience;
}
