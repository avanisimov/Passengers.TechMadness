import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'repositories.dart';
import 'audience_list.dart';
import 'audience_detail.dart';
import 'compaign_detail.dart';

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
        title: 'Tech.Madness',
        theme: ThemeData(
            primarySwatch: Colors.blue, accentColor: Colors.blueAccent),
        home: ChangeNotifierProvider.value(
          value: AudienceListModel(repository),
          child: AudienceListPage(),
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
              ),
          '/create-compaign': (context) => ChangeNotifierProvider.value(
                child: EditCompaignPage(),
                value: EditCompaignModel(),
              )
        });
  }
}
