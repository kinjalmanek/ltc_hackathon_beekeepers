// models.dart
class Companion {
  final String name;
  final String email;
  final List<Access> accessList;

  Companion({
    required this.name,
    required this.email,
    required this.accessList,
  });

  factory Companion.fromJson(Map<String, dynamic> json) {
    var accessListJson = json['accessList'] as List;
    List<Access> accessList = accessListJson.map((i) => Access.fromJson(i)).toList();
    return Companion(
      name: json['name'],
      email: json['email'],
      accessList: accessList,
    );
  }
}

class Access {
  final bool canView;
  final bool canPay;
  final bool balanceCheck;
  final Loan? loan;
  final Bill? bill;
  final DateTime accessStartDate;
  final DateTime? accessEndDate;
  final bool permanent;

  Access({
    required this.canView,
    required this.canPay,
    required this.balanceCheck,
    this.loan,
    this.bill,
    required this.accessStartDate,
    this.accessEndDate,
    required this.permanent,
  });

  factory Access.fromJson(Map<String, dynamic> json) {
    return Access(
      canView: json['canView'],
      canPay: json['canPay'],
      balanceCheck: json['balanceCheck'],
      loan: json['loan'] != null ? Loan.fromJson(json['loan']) : null,
      bill: json['bill'] != null ? Bill.fromJson(json['bill']) : null,
      accessStartDate: DateTime.parse(json['accessStartDate']),
      accessEndDate: json['accessEndDate'] != null ? DateTime.parse(json['accessEndDate']) : null,
      permanent: json['permanent'],
    );
  }
}

class Loan {
  final String portfolioId;
  final String loanType;
  final String loanTenure;
  final String loanAmount;

  Loan({
    required this.portfolioId,
    required this.loanType,
    required this.loanTenure,
    required this.loanAmount,
  });

  factory Loan.fromJson(Map<String, dynamic> json) {
    return Loan(
      portfolioId: json['portfolioId'],
      loanType: json['loanType'],
      loanTenure: json['loanTenure'],
      loanAmount: json['loanAmount'],
    );
  }
}

class Bill {
  final String portfolioId;
  final String billType;
  final String billAmount;
  final DateTime billDate;
  final String billFrequency;

  Bill({
    required this.portfolioId,
    required this.billType,
    required this.billAmount,
    required this.billDate,
    required this.billFrequency,
  });

  factory Bill.fromJson(Map<String, dynamic> json) {
    return Bill(
      portfolioId: json['portfolioId'],
      billType: json['billType'],
      billAmount: json['billAmount'],
      billDate: DateTime.parse(json['billDate']),
      billFrequency: json['billFrequency'],
    );
  }
}
