class AddUserToActivities < ActiveRecord::Migration[7.0]
  def change
    remove_reference :activities, :patient
    add_reference :activities, :user, null: false, foreign_key: true
  end
end
