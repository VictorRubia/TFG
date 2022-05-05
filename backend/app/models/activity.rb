class Activity < ApplicationRecord
  belongs_to :user
  has_many :ppg_measures, dependent: :destroy
  has_many :tags, dependent: :destroy
end
