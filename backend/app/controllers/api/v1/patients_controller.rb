class Api::V1::PatientsController < ApplicationController
  before_action :test_password, only: [:index, :get_api_key]
  # GET /patients
  def index
    @patients = Patient.find_by( username: params[:username])
    render json: @patients
  end

  def get_api_key
    @patients = Patient.find_by( username: params[:username])
    render json: @patients.private_api_key
  end

  # GET  /patients/:id
  def show
    @patients = Patient.find(params[:id])
    render json: @patients
  end

  # POST /patients
  def create
    @patients = Patient.new(patient_params)
    if @patients.save
      render json: @patients
    else
      render error: { error: 'Unable to create Patient.' }, status: 400
    end
  end
  # PUT /patients/:id
  def update
    @patients = Patient.find(params[:id])
    if @patients
      @patients.update(patient_params)
      render json: { message: 'User successfully update. '}, status:200
    else
      render json: { error: 'Unable to update user. '}, status:400
    end
  end
  # DELETE /patients/:id
  def destroy
    @patients = Patient.find(params[:id])
    if @patients
      @patients.destroy
      render json: { message: 'User successfully deleted. '}, status: 200
    else
      render json: { error: 'Unable to delete User. '}, status: 400
    end
  end
  private
  def patient_params
    params.require(:patient).permit(:name, :surname, :username, :password)
  end
  def test_password
    @patients = Patient.find_by( username: params[:username])
    if !@patients.nil?
      render json: { message: "No autorizado" }, status: :unauthorized unless @patients.password_hash == params[:password_hash]
    else
      render json: { message: "No autorizado" }, status: :unauthorized
    end
  end
end
