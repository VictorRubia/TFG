// Copyright 2022 Andrious Solutions Ltd. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:tfg/src/view.dart';
import 'package:tfg/src/app/model/Patient.dart';

/// This separate class represents 'the Model' (the data) of the App.
class Model extends ModelMVC {
  factory Model([StateMVC? state]) => _this ??= Model._(state);
  Model._(StateMVC? state) : super(state);
  static Model? _this;
  final lightScheme = ColorScheme.fromSeed(seedColor: Colors.cyan);

  late Patient patient;
  late String username;
  late String password;

  late String api_KEY;
}
