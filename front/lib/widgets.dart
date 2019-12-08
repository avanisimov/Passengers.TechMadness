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