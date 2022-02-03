class CreateMeasures < ActiveRecord::Migration[7.0]
  def change
    create_table :measures do |t|
      t.references :patient, null: false, foreign_key: true
      t.integer :hr
      t.decimal :grades
      t.decimal :minutes
      t.decimal :seconds
      t.integer :steps
      t.float :accelerometer_X
      t.float :accelerometer_Y
      t.float :accelerometer_Z
      t.float :gyroscope_X
      t.float :gyroscope_Y
      t.float :gyroscope_Z

      t.timestamps
    end
  end
end
