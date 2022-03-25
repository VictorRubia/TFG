class User < ApplicationRecord
  lockbox_encrypts :private_api_key
  blind_index :private_api_key

  before_create :set_private_api_key

  validates :private_api_key, uniqueness: true, allow_blank: true

  has_many :activities, dependent: :destroy

  validates :name, :surname, :email, :password_digest , presence: true
  validates :email, uniqueness: true


  private
  def set_private_api_key
    self.private_api_key = SecureRandom.hex if self.private_api_key.nil?
  end
end