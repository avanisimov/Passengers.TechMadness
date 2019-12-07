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
  List<AudienceShort> widgets = [];

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
    final AudienceShort item = widgets[i];
    return Card(
      child: InkWell(
        splashColor: Colors.blue.withAlpha(30),
        onTap: () {
          print('Card tapped.');
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
    String dataURL =
        "http://167.71.48.207:9999/api/web/audiences?skip=0&take=20";
    print("Start request for audience list url=$dataURL");
    http.Response response = await http.get(dataURL).catchError((error) => print("error happened on request ${error}"));
    print("Response is retrieved response=${response.body}");
    AudienceShortListResponse listResponse =
        AudienceShortListResponse.fromJson(json.decode(response.body));
    print(listResponse);
    setState(() {
      widgets = listResponse.items;
    });
  }
}

//---------------------------------------------------------------------------------
// Models
//---------------------------------------------------------------------------------

class AudienceShort {
  final String id;
  final String title;
  final String description;

  AudienceShort(this.id, this.title, this.description);

  factory AudienceShort.fromJson(Map<String, dynamic> json) {
    return AudienceShort(json['id'], json['title'], json['description']);
  }

  Map<String, dynamic> toJson() =>
      {'id': id, 'title': title, 'description': description};
}

class AudienceShortListResponse {
  final int total;
  final List<AudienceShort> items;

  AudienceShortListResponse(this.total, this.items);

  factory AudienceShortListResponse.fromJson(Map<String, dynamic> json) {
    List<AudienceShort> items =
        (json['items'] as Iterable).map((entry) => AudienceShort.fromJson(entry)).toList();
    return new AudienceShortListResponse(json['total'], items);
  }
}
