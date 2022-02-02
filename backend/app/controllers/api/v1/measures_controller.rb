class Api::V1::MeasuresController < Api::V1::BaseController
  before_action  :find_measure, :authorize_post, only: [:show, :update, :destroy]
  def index
    @measures = Measure.all
    render json: @measures
  end
  def show
    render json: @measures
  end
  def create
    @measures = Measure.new(measure_params)
    if @measures.save
      render json: @measures
    else
      render error: { error: 'Unable to create Measure.'}, status: 400
    end
  end
  def update
    if @measures
      @measures.update(measure_params)
      render json: { message: 'Measure successfully update.'}, status:200
    else
      render json: { error: 'Unable to update Measure.'}, status:400
    end
  end
  def destroy
    if @measures
      @measures.destroy
      render json: { message: 'Measure successfully deleted.'}, status:200
    else
      render json: { error: 'Unable to delete Measure. '}, status:400
    end
  end
  private
  def measure_params
    params.require(:measure).permit(:hr, :grades, :minutes, :seconds, :steps, :accelerometer_X, :accelerometer_Y, :accelerometer_Z, :gyroscope_X, :gyroscope_Y, :gyroscope_Z, :patient_id)
  end
  def find_measure
    @measures = Measure.find(params[:id])
  end
  def authorize_post
    render json: { message: "Acceso NO autorizado" }, status: :unauthorized unless @patient.id == @measures.patient_id
  end
end
