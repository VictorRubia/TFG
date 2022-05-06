class ChangeTagToTags < ActiveRecord::Migration[7.0]
  def change
    change_column :tags, :tag, :json
  end
end
