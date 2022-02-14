// Copyright 2022 Andrious Solutions Ltd. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:tfg/src/view.dart';
import 'package:tfg/src/model.dart';
import 'package:crypt/crypt.dart';
import 'package:watch_connectivity/watch_connectivity.dart';

class Controller extends ControllerMVC {
  factory Controller([StateMVC? state]) => _this ??= Controller._(state);
  Controller._(StateMVC? state)
      : _model = Model(),
        super(state);
  static Controller? _this;

  final Model _model;

  final BASE_URL = '192.168.1.132:3000';
  final GET_API_URL = '/api/v1/patients/get_api_key/';

  /// Note, the count comes from a separate class, _Model.
  String get username => _model.username;
  String get password => _model.password;
  get api_KEY => _model.api_KEY;

  Patient get patient => _model.patient;

  set username(value) {
    _model.username = value;
  }
  set password(value) {
    _model.password = value;
  }
  set api_KEY(value) {
    _model.api_KEY = value;
  }
  set patient(value) {
    _model.patient = value;
  }

  Future<bool> obtainAPIKEY(String username, String password) async{
    this.username = username;
    this.password = password;
    final parameters = { 'username' : username, 'password_hash' : password};
    final uri = Uri.http(BASE_URL, GET_API_URL, parameters);
      Response r = await http.get(uri);
      if(r.statusCode == 200){
        print('API KEY: ${r.body}');
        api_KEY = r.body;
        return true;
      } else {
      return false;
    }
  }

  Future<bool> unloadModel() async {
    final prefs = await SharedPreferences.getInstance();
    final successUsername = await prefs.remove('com.victorrubia.tfg:username');
    final successPassword = await prefs.remove('com.victorrubia.tfg:password');

    if(successPassword && successUsername){
      username = '';
      password = '';
      api_KEY = '';
      print('Successfully exited');
      return true;
    }
    else{
      return false;
    }
  }

  Future<bool> loadModel() async {
    final prefs = await SharedPreferences.getInstance();
    if(prefs.containsKey('com.victorrubia.tfg:username')){
      username = prefs.getString('com.victorrubia.tfg:username')!;
      password = prefs.getString('com.victorrubia.tfg:password')!;
    }
    if (await obtainAPIKEY(username, password)) {
      final uri = Uri.http(BASE_URL, GET_API_URL);
      Response r = await http.get(uri, headers: {
        'Authorization': 'Bearer $api_KEY',
      });
      if (r.statusCode == 200) {
        patient = Patient.fromJson(jsonDecode(r.body));

        if(!prefs.containsKey('com.victorrubia.tfg:username')) {
          await prefs.setString('com.victorrubia.tfg:username', username);
          await prefs.setString('com.victorrubia.tfg:password', password);
        }
        return true;
      } else {
        return false;
      }
    }
    else {
      return false;
    }
  }

  Future<bool> watchConnected() async{
    final watch = WatchConnectivity();

    return (await watch.isPaired && await watch.isReachable);
  }

}
