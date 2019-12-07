//---------------------------------------------------------------------------------
// Models
//---------------------------------------------------------------------------------

import 'package:flutter/material.dart';

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

  RangeValues get range =>
      RangeValues(startValue.toDouble(), endValue.toDouble());
}
