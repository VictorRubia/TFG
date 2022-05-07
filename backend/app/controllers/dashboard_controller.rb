class DashboardController < ApplicationController
  def index
  end

  def create_user
  end

  def view_activities
    @user = User.find(params[:id])
  end

  def get_activities
    @activities = Activity.where(user_id: params[:id]).order('start_d DESC')
  end

  def get_activities_sorted
    @activities = Activity.where(user_id: params[:id]).order('end_d DESC')
    @grouped_activities = @activities.group_by{ |t| t.end_d.to_date == DateTime.now.to_date }

    if @grouped_activities[false].present?
      #Create month wise groups of messages
      @day_wise_sorted_activities  = @grouped_activities[false].group_by{ |t| t.created_at.month}
    end
  end

  def activity_details
    @activity = Activity.find(params[:id_activity])
  end

  helper_method :get_activities, :get_activities_sorted, :activity_details

end
