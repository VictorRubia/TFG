import 'package:flutter/material.dart';
import 'package:wear/wear.dart';
import 'package:watch_connectivity/watch_connectivity.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final _watch = WatchConnectivity();

  var _count = 0;

  var _paired = false;
  var _reachable = false;
  var _context = <String, dynamic>{};
  var _receivedContexts = <Map<String, dynamic>>[];
  final _log = <String>[];

  @override
  void initState() {
    super.initState();
    initPlatformState();

    _watch.messageStream
        .listen((e) => setState(() => _log.add('Received message: $e')));
    _watch.contextStream
        .listen((e) => setState(() => _log.add('Received context: $e')));
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    _paired = await _watch.isPaired;
    _reachable = await _watch.isReachable;
    _context = await _watch.applicationContext;
    _receivedContexts = await _watch.receivedApplicationContexts;
    _watch.messageStream.listen((data) {
      print("DataReceived: " + data.toString());
    }, onDone: () {
      print("Task Done");
    }, onError: (error) {
      print("Some Error");
    });

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: WatchShape(
            builder: (BuildContext context, WearShape shape, Widget? child) {
              return Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  CustomScrollView(
                    shrinkWrap: true,
                    slivers: <Widget>[
                      SliverPadding(
                        padding: const EdgeInsets.all(20.0),
                        sliver: SliverList(
                          delegate: SliverChildListDelegate(
                            <Widget>[
                              TextButton(
                                child: const Text('Refresh'),
                                onPressed: initPlatformState,
                              ),
                              const SizedBox(height: 8),
                              const Text('Send'),
                              const Text('Log'),
                              ..._log.reversed.map((e) => Text(e)),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),

                  child!,
                ],
              );
            },
            child: AmbientMode(
              builder: (BuildContext context, WearMode mode, Widget? child) {
                return Container();
              },
            ),
          ),
        ),
      ),
    );
  }
}
