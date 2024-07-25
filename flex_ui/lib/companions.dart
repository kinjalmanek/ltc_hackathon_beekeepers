import 'package:flex_users/companion_edit.dart';
import 'package:flutter/material.dart';
import 'models.dart'; // Import the models file

class CompanionsScreen extends StatefulWidget {
  final List<dynamic> companionsList;

  CompanionsScreen({required this.companionsList});

  @override
  _CompanionsScreenState createState() => _CompanionsScreenState();
}

class _CompanionsScreenState extends State<CompanionsScreen> {
  @override
  Widget build(BuildContext context) {
    List<Companion> companions = widget.companionsList
        .map((json) => Companion.fromJson(json))
        .toList();

    return Scaffold(
      appBar: AppBar(
        title: Text('Companions'),
      ),
      body: SafeArea(
        child: ListView.builder(
          itemCount: companions.length,
          itemBuilder: (context, index) {
            return _buildCompanionCard(companions[index]);
          },
        ),
      ),
    );
  }

  Widget _buildCompanionCard(Companion companion) {
    return Card(
      elevation: 4.0,
      margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              companion.name,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            Text(
              companion.email,
              style: TextStyle(fontSize: 16, color: Colors.grey[600]),
            ),
            SizedBox(height: 10),
            ...companion.accessList.map((access) => _buildAccessDetails(access)).toList(),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () {
                navigateToEditScreen(companion);
              },
              child: Text('Edit'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAccessDetails(Access access) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text('Access: ${access.permanent ? "Permanent" : "Temporary"}'),
        if (access.loan != null) _buildLoanDetails(access.loan!),
        if (access.bill != null) _buildBillDetails(access.bill!),
        SizedBox(height: 10),
      ],
    );
  }

  Widget _buildLoanDetails(Loan loan) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text('Loan Type: ${loan.loanType}'),
        Text('Amount: \$${loan.loanAmount}'),
        Text('Tenure: ${loan.loanTenure} years'),
      ],
    );
  }

  Widget _buildBillDetails(Bill bill) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text('Bill Type: ${bill.billType}'),
        Text('Amount: \$${bill.billAmount}'),
        Text('Date: ${_formatDate(bill.billDate.toString())}'),
        Text('Frequency: ${bill.billFrequency}'),
      ],
    );
  }

  String _formatDate(String dateString) {
    DateTime date = DateTime.parse(dateString);
    return '${date.toLocal().toString().split(' ')[0]} ${date.toLocal().toString().split(' ')[1]}';
  }

  void navigateToEditScreen(Companion companion) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => CompanionEditScreen(companion: companion),
      ),
    );
  }
}
