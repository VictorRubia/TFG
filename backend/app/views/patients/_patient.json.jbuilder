json.extract! patient, :id, :name, :surname, :username, :password_hash, :created_at, :updated_at
json.url patient_url(patient, format: :json)
