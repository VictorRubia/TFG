// Copyright 2022 Andrious Solutions Ltd. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:tfg/src/view.dart';
import 'package:tfg/src/controller.dart';
import 'package:skeletons/skeletons.dart';
import 'package:watch_connectivity/watch_connectivity.dart';

/// The Home page
class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, this.title = 'Flutter Demo'}) : super(key: key);

  // Fields in a StatefulWidget should always be "final".
  final String title;

  @override
  State createState() => _MyHomePageState();
}

/// This 'MVC version' is a subclass of the State class.
/// This version is linked to the App's lifecycle using [WidgetsBindingObserver]
class _MyHomePageState extends StateMVC<MyHomePage> {
  /// Let the 'business logic' run in a Controller
  _MyHomePageState() : super(Controller()) {
    /// Acquire a reference to the passed Controller.
    con = controller as Controller;
  }

  late Controller con;
  final watch = WatchConnectivity();

  @override
  void initState() {
    /// Look inside the parent function and see it calls
    /// all it's Controllers if any.
    super.initState();

    /// Retrieve the 'app level' State object
    appState = rootState!;

    /// You're able to retrieve the Controller(s) from other State objects.
    var con = appState.controller;

    con = appState.controllerByType<AppController>();

    con = appState.controllerById(con?.keyId);

  }

  late AppStateMVC appState;

  /// This is 'the View'; the interface of the home page.
  @override
  Widget build(BuildContext context) => Scaffold(
    appBar: AppBar(
      title: Text('TFG: Estrés en Transportes'),
      actions: <Widget>[
        IconButton(
          icon: const Icon(Icons.refresh),
          tooltip: 'Show Snackbar',
          onPressed: () {
            ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Refreshing')));
          },
        ),
      ],
      automaticallyImplyLeading: false,
    ),
    body: Padding(
      padding: EdgeInsets.all(16),
      child: FutureBuilder<bool>(
            future: con.loadModel(),//Future.delayed(Duration(seconds: 2))
                //.then((onValue) => true),//con.loadModel('',''),
            builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
              List<Widget> children;
              if (snapshot.hasData) {
                children = <Widget>[
                  Text(con.patient.name, style: Theme.of(context).textTheme.headline3,),
                  Text(con.patient.surname, style: Theme.of(context).textTheme.headline4,),

                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.08,
                  ),
                  Row(
                    children: [
                      Text('Status Wear OS Connection: ', style: FontTheming.subtitle1,),
                      FutureBuilder<bool>(
                        future: con.watchConnected(), // a previously-obtained Future<String> or null
                        builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
                          List<Widget> children;
                          if (snapshot.hasData) {
                            children = <Widget>[
                              snapshot.data! ? const Icon(
                                Icons.check_circle_outline,
                                color: Colors.green,
                              ) : const Icon(
                                Icons.error_outline,
                                color: Colors.red,
                              ),
                            ];
                          } else if (snapshot.hasError) {
                            children = <Widget>[
                              const Icon(
                                Icons.error_outline,
                                color: Colors.red,
                              ),
                            ];
                          } else {
                            children = const <Widget>[
                              SkeletonAvatar(
                                style: SkeletonAvatarStyle(
                                    shape: BoxShape.circle, height: 30, width: 30),
                              ),
                            ];
                          }
                          return Column(
                              children: children,
                            );
                        },
                      ),
                    ],
                  ),
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.3,
                  ),

                  SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        ElevatedButton(
                          onPressed: () async {
                            watch.sendMessage({'data': 'JC ILY'});
                          },
                          child: const Text(
                            'Mandar mensaje',
                            style: TextStyle(fontSize: 18),
                          ),
                          style: ElevatedButton.styleFrom(
                              fixedSize: Size(
                                  MediaQuery.of(context).size.width * 0.8,
                                  MediaQuery.of(context).size.height * 0.08),
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(50))
                          ),
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        ElevatedButton(
                          onPressed: () async {
                            con.unloadModel();
                            Navigator.push(
                              context,
                              MaterialPageRoute(builder: (context) => const MyLoginPage()),
                            );
                          },
                          child: const Text(
                            'Cambiar de Usuario',
                            style: TextStyle(fontSize: 18),
                          ),
                          style: ElevatedButton.styleFrom(
                              fixedSize: Size(
                                  MediaQuery.of(context).size.width * 0.8,
                                  MediaQuery.of(context).size.height * 0.08),
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(50))
                          ),
                        ),
                      ],
                    ),
                  ),
                ];
              } else if (snapshot.hasError) {
                children = <Widget>[
                  const Icon(
                    Icons.error_outline,
                    color: Colors.red,
                    size: 60,
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 16),
                    child: Text('Error: ${snapshot.error}'),
                  )
                ];
              } else {
                children = <Widget>[
                  SkeletonLine(
                    style: SkeletonLineStyle(
                        borderRadius: BorderRadius.circular(8),
                        height: 50,
                        randomLength: true),
                  ),
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.01,
                  ),
                  SkeletonLine(
                    style: SkeletonLineStyle(
                        borderRadius: BorderRadius.circular(8),
                        height: 45,
                        randomLength: true),
                  ),
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.08,
                  ),
                  Row(
                    children: [
                      Text('Status Wear OS Connection: ', style: FontTheming.subtitle1,),
                      const SkeletonAvatar(
                        style: SkeletonAvatarStyle(
                            shape: BoxShape.circle, height: 30, width: 30),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.3,
                  ),
                  SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        ElevatedButton(
                          onPressed: () async {
                            con.unloadModel();
                            Navigator.push(
                              context,
                              MaterialPageRoute(builder: (context) => const MyLoginPage()),
                            );
                          },
                          child: const Text(
                            'Cambiar de Usuario',
                            style: TextStyle(fontSize: 18),
                          ),
                          style: ElevatedButton.styleFrom(
                              fixedSize: Size(
                                  MediaQuery.of(context).size.width * 0.8,
                                  MediaQuery.of(context).size.height * 0.08),
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(50))
                          ),
                        ),
                      ],
                    ),
                  )
                ];
              }
              return Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: children,
                );
            },
          ),
          //Text('${con.patient.name}', style: FontTheming.title3,),
          //Text('${con.patient.surname}', style: FontTheming.title4,),
      ),
  );

  /// Supply an error handler for Unit Testing.
  @override
  void onError(FlutterErrorDetails details) {
    /// Error is now handled.
    super.onError(details);
  }
}
