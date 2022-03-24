class Activity < ApplicationRecord
  belongs_to :user
  has_many :ppg_measures, dependent: :destroy
end
