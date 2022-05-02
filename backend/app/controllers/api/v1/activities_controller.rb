class Api::V1::ActivitiesController < Api::V1::BaseController
  before_action :set_activity, only: [:show, :update, :destroy]
  # before_action :authorize_activity, only: [:create]

  def index
    @activities = @user.activities
  end

  def show
  end

  def create
    @activity = @user.activities.build(activity_params)
    if @activity.save
      render :show, status: :created
    else
      render json: { message: @activity.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @activity.update(activity_params)
      render :show, status: :ok
    else
      render json: { message: @activity.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @activity.destroy
    render :show, status: :ok
  end

  private

  def set_activity
    @activity = Activity.find(params[:id])
  end

  def authorize_activity
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def activity_params
    params.require(:activity).permit(:name, :start_d, :end_d)
  end
end
