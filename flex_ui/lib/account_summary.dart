import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'companion_form.dart';
import 'companions.dart'; // Import the CompanionsScreen

class BankAccountSummaryScreen extends StatefulWidget {
  final String loggedInUser;
  BankAccountSummaryScreen({required this.loggedInUser});

  @override
  _BankAccountSummaryScreenState createState() => _BankAccountSummaryScreenState();
}

class _BankAccountSummaryScreenState extends State<BankAccountSummaryScreen> {
  String accountNumber = 'XXXX-XXXX-1234'; // Replace with actual account number
  double accountBalance = 0.0; // Initialize balance
  bool isLoading = true; // Loading indicator
  List<dynamic> companionsList = []; // To store companions data

  @override
  void initState() {
    super.initState();
    // Fetch data when the screen loads
    fetchData();
  }

  Future<void> fetchData() async {
    final String apiUrl = 'https://ltc-hackathon-beekeepers-wct627xdfq-el.a.run.app/portfolio?email=${widget.loggedInUser}';

    try {
      // Make GET request
      final response = await http.get(Uri.parse(apiUrl));

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        setState(() {
          accountNumber = responseData['accountNumber'];
          accountBalance = double.parse(responseData['accountBalance']);
          companionsList = responseData['companions']; // Update companions list
          isLoading = false;
        });
      } else {
        throw Exception('Failed to load data');
      }
    } catch (e) {
      print('Error: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bank Account Summary'),
      ),
      body: SafeArea(
        child: isLoading
            ? Center(child: CircularProgressIndicator())
            : Container(
          width: double.infinity,
          padding: EdgeInsets.symmetric(horizontal: 30, vertical: 20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: 20),
              Text(
                'Account Number',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 8),
              Text(
                accountNumber,
                style: TextStyle(
                  fontSize: 20,
                ),
              ),
              SizedBox(height: 20),
              Text(
                'Account Balance',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 8),
              Text(
                '\$$accountBalance',
                style: TextStyle(
                  fontSize: 28,
                ),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => CompanionFormScreen(),
                    ),
                  );
                },
                style: ElevatedButton.styleFrom(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(50),
                  ),
                  padding: EdgeInsets.symmetric(horizontal: 40, vertical: 16),
                ),
                child: Text(
                  'Add Companion',
                  style: TextStyle(
                    fontSize: 18,
                  ),
                ),
              ),
              SizedBox(height: 40),
              Expanded(
                child: GridView.count(
                  crossAxisCount: 2,
                  mainAxisSpacing: 20,
                  crossAxisSpacing: 20,
                  children: <Widget>[
                    _buildServiceItem(
                      icon: Icons.people,
                      label: 'Companions',
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => CompanionsScreen(companionsList: companionsList),
                          ),
                        );
                      },
                    ),
                    _buildServiceItem(
                      icon: Icons.favorite,
                      label: 'Admirers',
                      onTap: () {
                        // Navigate to admirers screen or perform action
                      },
                    ),
                    _buildServiceItem(
                      icon: Icons.home,
                      label: 'Loans',
                      onTap: () {
                        // Navigate to loans screen or perform action
                      },
                    ),
                    _buildServiceItem(
                      icon: Icons.receipt,
                      label: 'Bills',
                      onTap: () {
                        // Navigate to bills screen or perform action
                      },
                    ),
                    _buildServiceItem(
                      icon: Icons.account_balance_wallet,
                      label: 'Funds',
                      onTap: () {
                        // Navigate to funds screen or perform action
                      },
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildServiceItem({required IconData icon, required String label, required VoidCallback onTap}) {
    return GestureDetector(
      onTap: onTap,
      child: Card(
        elevation: 2.0,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12.0)),
        child: Padding(
          padding: EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Icon(
                icon,
                size: 48.0,
                color: Colors.blue,
              ),
              SizedBox(height: 8.0),
              Text(
                label,
                style: TextStyle(
                  fontSize: 18.0,
                  fontWeight: FontWeight.bold,
                ),
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
