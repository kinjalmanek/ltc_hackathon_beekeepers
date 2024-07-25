import 'package:flutter/material.dart';

class CompanionFormScreen extends StatefulWidget {
  @override
  _CompanionFormScreenState createState() => _CompanionFormScreenState();
}

class _CompanionFormScreenState extends State<CompanionFormScreen> {
  // Form fields
  String name = '';
  String email = '';
  String countryCode = '';
  String phoneNumber = '';
  String accessLevel = '';
  DateTime? startDate;
  DateTime? endDate;

  // Radio button values
  String? accessType;

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
                  Radio<String>(
                    value: 'permanent',
                    groupValue: accessType,
                    onChanged: (value) {
                      setState(() {
                        accessType = value;
                        accessLevel = 'Permanent Access';
                        // Reset start date and end date when switching access type
                        startDate = null;
                        endDate = null;
                      });
                    },
                  ),
                  Text('Permanent Access'),
                  SizedBox(width: 20.0),
                  Radio<String>(
                    value: 'full',
                    groupValue: accessType,
                    onChanged: (value) {
                      setState(() {
                        accessType = value;
                        accessLevel = 'Full Account Access';
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
                      onPressed: accessType == 'permanent'
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
                      onPressed: accessType == 'permanent'
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

  void _addCompanion() {
    // Implement logic to add companion using the form data
    print('Name: $name');
    print('Email: $email');
    print('Phone Number: $countryCode $phoneNumber');
    print('Access Level: $accessLevel');
    print('Start Date: $startDate');
    print('End Date: $endDate');
  }
}
