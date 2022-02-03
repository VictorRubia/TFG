class Api::V1::MeasuresController < Api::V1::BaseController
  before_action :set_measure, only: [:show, :update, :destroy]
  before_action :authorize_measure, only: [:show, :update, :destroy]

  def index
    @measures = @patient.measures
  end

  def show
  end

  def create
    @measure = @patient.measures.build(measure_params)
    if @measure.save
      render :show, status: :created
    else
      render json: { message: @measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @measure.update(measure_params)
      render :show, status: :ok
    else
      render json: { message: @measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @measure.destroy
    render :show, status: :ok
  end

  private

  def set_measure
    @measure = Measure.find(params[:id])
  end

  def authorize_measure
    render json: { message: "No autorizado" }, status: :unauthorized unless @patient == @measure.patient
  end

  def measure_params
    params.require(:measure).permit(:title, :body)
  end
end
