class Patient::PrivateApiKeysController < ApplicationController

  before_action :authenticate

  def update
    puts params.inspect
    if Patient.find(params[:patient][:id]).update(private_api_key: SecureRandom.hex)
      redirect_to edit_patient_path(Patient.find(params[:patient][:id])), notice: "Clave API actualizada"
    else
      redirect_to edit_patient_path(Patient.find(params[:patient][:id])), alert: "Ha habido un error: #{current_user.errors.full_messages.to_sentence}"
    end
  end
end
