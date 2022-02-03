class CreatePatients < ActiveRecord::Migration[7.0]
  def change
    create_table :patients do |t|
      t.string :name
      t.string :surname
      t.string :username
      t.string :password_hash

      t.timestamps
    end
  end
end
