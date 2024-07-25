// loans_screen.dart

import 'package:flutter/material.dart';
import 'models.dart'; // Import the Loan model

class LoansScreen extends StatelessWidget {
  final List<Loan> loans;

  LoansScreen({required this.loans});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Loans'),
      ),
      body: SafeArea(
        child: ListView.builder(
          itemCount: loans.length,
          itemBuilder: (context, index) {
            return _buildLoanCard(loans[index]);
          },
        ),
      ),
    );
  }

  Widget _buildLoanCard(Loan loan) {
    return Card(
      elevation: 4.0,
      margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              loan.loanType,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 8),
            Text(
              'Amount: \$${loan.loanAmount}',
              style: TextStyle(fontSize: 16),
            ),
            Text(
              'Tenure: ${loan.loanTenure} years',
              style: TextStyle(fontSize: 16),
            ),
            SizedBox(height: 10),
          ],
        ),
      ),
    );
  }
}
