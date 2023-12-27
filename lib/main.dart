import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Dynamic App Icon',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const HomePage(),
    );
  }
}

const MethodChannel channel = MethodChannel("dynamic_app_icon_channel");

class HomePage extends StatelessWidget {
  const HomePage({super.key});
  Future<void> changeAppIcon(bool isFirstIcon) async {
    try {
      String? value = await channel
          .invokeMethod("changeAppIcon", {"isFirstIcon": isFirstIcon});
      print(value);
    } catch (e) {
      print(e.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                  onPressed: () {
                    changeAppIcon(true);
                  },
                  child: const Text("Big Icon")),
              ElevatedButton(
                  onPressed: () {
                    changeAppIcon(false);
                  },
                  child: const Text("Small Icon")),
            ],
          )
        ],
      ),
    );
  }
}
