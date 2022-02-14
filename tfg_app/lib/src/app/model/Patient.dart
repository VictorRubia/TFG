class Patient{
  late String name;
  late String surname;

  Patient(this.name, this.surname);

  Patient.fromJson(Map<String, dynamic> json)
    : name = json['name'],
      surname = json['surname'];
}