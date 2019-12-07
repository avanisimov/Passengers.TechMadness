import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(SampleApp());
}

class SampleApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sample App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: SampleAppPage(),
    );
  }
}

class SampleAppPage extends StatefulWidget {
  SampleAppPage({Key key}) : super(key: key);

  @override
  _SampleAppPageState createState() => _SampleAppPageState();
}

class _SampleAppPageState extends State<SampleAppPage> {
  List<String> widgets = [];

  @override
  void initState() {
    super.initState();

    loadData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Sample App"),
        ),
        body: ListView.builder(
            itemCount: widgets.length,
            itemBuilder: (BuildContext context, int position) {
              return getRow(position);
            }));
  }

  Widget getRow(int i) {
    return Center(
      child: Card(
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          onTap: () {
            print('Card tapped.');
          },
          child: Container(
            width: 300,
            height: 100,
            child: Text("${widgets[i]}"),
          ),
        ),
      ),
    );
  }

  loadData() async {
    setState(() {
      widgets = [
        "Уши от мертвого осла",
        "Рога и копыта",
        "Хвост мертвого осла",
        "Не хотите ли апельсинки?",
        "Самое лучше предложиние в мире",
        "Два ничего по цене трех",
        "Азазазаза!!!!!!!!!!!!!!!!!!!!!!!!!!",
        "Хвост мертвого осла",
        "Не хотите ли апельсинки?",
        "Самое лучше предложиние в мире",
        "Два ничего по цене трех",
        "Азазазаза!!!!!!!!!!!!!!!!!!!!!!!!!!",
        "Hello world!"
      ];
    });
  }
}

class ListRaw extends StatelessWidget {
  final String title;

  ListRaw(this.title);

  @override
  Widget build(BuildContext context) {
    return Text(title);
  }
}
