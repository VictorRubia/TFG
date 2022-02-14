import 'package:flutter/material.dart';

class FontTheming{

  static TextStyle get title1 => const TextStyle(
    color: Color(0xFF303030),
    fontWeight: FontWeight.w600,
    fontSize: 24,
  );

  static TextStyle get title2 => const TextStyle(
    color: Color(0xFF303030),
    fontWeight: FontWeight.w500,
    fontSize: 22,
  );

  static TextStyle get title3 => const TextStyle(
    color: Color(0xFF303030),
    fontWeight: FontWeight.w300,
    fontSize: 50,
  );
  static TextStyle get title4 => const TextStyle(
    color: Color(0xFF303030),
    fontWeight: FontWeight.w200,
    fontSize: 40,
  );

  static TextStyle get subtitle1 => const TextStyle(
    color: Color(0xFF757575),
    fontWeight: FontWeight.w500,
    fontSize: 18,
  );

  static TextStyle get subtitle2 => const TextStyle(
    color: Color(0xFF616161),
    fontWeight: FontWeight.normal,
    fontSize: 16,
  );

  static TextStyle get bodyText1 => const TextStyle(
    color: Color(0xFF303030),
    fontWeight: FontWeight.normal,
    fontSize: 14,
  );

  static TextStyle get bodyText2 => const TextStyle(
    color: Color(0xFF424242),
    fontWeight: FontWeight.normal,
    fontSize: 14,
  );

}