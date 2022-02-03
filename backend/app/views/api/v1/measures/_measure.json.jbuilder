json.extract! measure, :id, :patient_id, :hr, :grades, :minutes, :seconds, :steps, :accelerometer_X, :accelerometer_Y, :accelerometer_Z, :gyroscope_X, :gyroscope_Y, :gyroscope_Z, :created_at, :updated_at
json.url api_v1_measure_url(measure, format: :json)
