import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class CompanionFormScreen extends StatefulWidget {
  final String portfolioId;

  CompanionFormScreen({required this.portfolioId});

  @override
  _CompanionFormScreenState createState() => _CompanionFormScreenState();
}

class _CompanionFormScreenState extends State<CompanionFormScreen> {
  // Form fields
  String name = '';
  String email = '';
  String countryCode = '';
  String phoneNumber = '';
  DateTime? startDate;
  DateTime? endDate;

  // Checkbox values
  bool isPermanentAccess = false;
  bool isFullAccess = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Add Companion'),
      ),
      body: Padding(
        padding: EdgeInsets.all(20.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              TextField(
                decoration: InputDecoration(labelText: 'Name'),
                onChanged: (value) {
                  setState(() {
                    name = value;
                  });
                },
              ),
              SizedBox(height: 20.0),
              TextField(
                decoration: InputDecoration(labelText: 'Email'),
                onChanged: (value) {
                  setState(() {
                    email = value;
                  });
                },
              ),
              SizedBox(height: 20.0),
              Row(
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: TextField(
                      decoration: InputDecoration(labelText: 'Country Code'),
                      onChanged: (value) {
                        setState(() {
                          countryCode = value;
                        });
                      },
                    ),
                  ),
                  SizedBox(width: 10.0),
                  Expanded(
                    flex: 2,
                    child: TextField(
                      decoration: InputDecoration(labelText: 'Phone Number'),
                      onChanged: (value) {
                        setState(() {
                          phoneNumber = value;
                        });
                      },
                    ),
                  ),
                ],
              ),
              SizedBox(height: 20.0),
              Text('Access Level', style: TextStyle(fontSize: 18.0)),
              Row(
                children: <Widget>[
                  Checkbox(
                    value: isPermanentAccess,
                    onChanged: (value) {
                      setState(() {
                        isPermanentAccess = value ?? false;
                      });
                    },
                  ),
                  Text('Permanent Access'),
                ],
              ),
              Row(
                children: <Widget>[
                  Checkbox(
                    value: isFullAccess,
                    onChanged: (value) {
                      setState(() {
                        isFullAccess = value ?? false;
                      });
                    },
                  ),
                  Text('Full Account Access'),
                ],
              ),
              SizedBox(height: 20.0),
              Row(
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: ElevatedButton(
                      onPressed: isPermanentAccess
                          ? null
                          : () {
                        _selectStartDate(context);
                      },
                      child: Text('Select Start Date'),
                    ),
                  ),
                  SizedBox(width: 10.0),
                  Expanded(
                    flex: 1,
                    child: ElevatedButton(
                      onPressed: isPermanentAccess
                          ? null
                          : () {
                        _selectEndDate(context);
                      },
                      child: Text('Select End Date'),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 20.0),
              ElevatedButton(
                onPressed: () {
                  _addCompanion();
                },
                child: Text('Add'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _selectStartDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (picked != null && picked != startDate) {
      setState(() {
        startDate = picked;
      });
    }
  }

  void _selectEndDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (picked != null && picked != endDate) {
      setState(() {
        endDate = picked;
      });
    }
  }

  Future<void> _addCompanion() async {
    final String apiUrl = 'https://your-api-url.com/add-companion'; // Replace with your API URL

    final Map<String, dynamic> data = {
      'name': name,
      'email': email,
      'countryCode': countryCode,
      'phoneNumber': phoneNumber,
      'permanentAccess': isPermanentAccess,
      'fullAccess': isFullAccess,
      'startDate': startDate?.toIso8601String(),
      'endDate': endDate?.toIso8601String(),
      'portfolioId': widget.portfolioId, // Include portfolioId in the request
    };

    try {
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode(data),
      );

      if (response.statusCode == 200) {
        // Handle success
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Companion added successfully')),
        );
        Navigator.pop(context); // Go back to the previous screen
      } else {
        // Handle error
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to add companion')),
        );
      }
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('An error occurred')),
      );
    }
  }
}
