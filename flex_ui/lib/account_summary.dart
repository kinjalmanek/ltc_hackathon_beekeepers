import 'package:flutter/material.dart';

class BankAccountSummaryScreen extends StatefulWidget {
  @override
  _BankAccountSummaryScreenState createState() => _BankAccountSummaryScreenState();
}

class _BankAccountSummaryScreenState extends State<BankAccountSummaryScreen> {
  String accountNumber = 'XXXX-XXXX-1234'; // Replace with actual account number
  double accountBalance = 5000.00; // Replace with actual account balance

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bank Account Summary'),
      ),
      body: SafeArea(
        child: Container(
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
              SizedBox(height: 40),
              Expanded(
                child: GridView.count(
                  crossAxisCount: 2,
                  mainAxisSpacing: 20,
                  crossAxisSpacing: 20,
                  children: <Widget>[
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
                      icon: Icons.people,
                      label: 'Companions',
                      onTap: () {
                        // Navigate to companions screen or perform action
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
