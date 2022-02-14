import 'package:flutter/services.dart';
import 'package:tfg/src/view.dart';

void main(){
  runApp(const MyApp(key: Key('MyApp')));
  SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(
    systemNavigationBarColor: Colors.black, // navigation bar color
    statusBarColor: ColorScheme.fromSeed(seedColor: Colors.cyan).primary, // status bar color
  ));
}