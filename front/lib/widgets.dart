import 'package:flutter/material.dart';

class ParameterCard extends StatelessWidget {
  final String title;
  final List<Widget> children;

  ParameterCard({String title, List<Widget> children})
      : this.title = title,
        this.children = children;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 0.5,
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
            Divider(color: Colors.transparent, height: 4,),
            ...children,
          ],
        ),
      ),
      margin: EdgeInsets.fromLTRB(0, 8, 24, 8),
    );
  }
}
