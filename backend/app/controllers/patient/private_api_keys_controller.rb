class Patient::PrivateApiKeysController < ApplicationController

  before_action :authenticate

  def update
    puts params.inspect
    if Patient.find(params[:patient][:id]).update(private_api_key: SecureRandom.hex)
      redirect_to dashboard_create_patient_path, notice: "Clave API actualizada"
    else
      redirect_to dashboard_create_patient_path, alert: "Ha habido un error: #{current_user.errors.full_messages.to_sentence}"
    end
  end
end
