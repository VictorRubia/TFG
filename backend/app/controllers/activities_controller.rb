require 'csv'

class ActivitiesController < ApplicationController
  before_action :set_activity, only: %i[ show edit update destroy ppg_measures ]

  # GET /activities or /activities.json
  def index
    @activities = Activity.all
  end

  # GET /activities/1 or /activities/1.json
  def show
  end

  # GET /activities/new
  def new
    @activity = Activity.new
  end

  # GET /activities/1/edit
  def edit
  end

  # POST /activities or /activities.json
  def create
    @activity = Activity.new(activity_params)

    respond_to do |format|
      if @activity.save
        format.html { redirect_to activity_url(@activity), notice: "Activity was successfully created." }
        format.json { render :show, status: :created, location: @activity }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activities/1 or /activities/1.json
  def update
    respond_to do |format|
      if @activity.update(activity_params)
        format.html { redirect_to activity_url(@activity), notice: "Activity was successfully updated." }
        format.json { render :show, status: :ok, location: @activity }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activities/1 or /activities/1.json
  def destroy
    # @ppg_measures = PpgMeasure.find_by id:
    user_id = @activity.user_id
    @activity.destroy

    respond_to do |format|
      # format.html { redirect_to activities_url, notice: "Activity was successfully destroyed." }
      format.html { redirect_to dashboard_view_activity_url, params: { id: user_id}, notice: "Actividad eliminada." }
      format.json { head :no_content }
    end
  end

  def export
    @activity = Activity.find(params[:id])

    @measures = @activity.ppg_measures

    master = @measures.map { |f| JSON.parse(f.measurement, object_class: OpenStruct) }.flatten
    @final = master

    respond_to do |format|
      format.csv do
        response.headers['Content-Type'] = 'text/csv'
        response.headers['Content-Disposition'] = "attachment; filename=export_" + @activity.id.to_s + ".csv"
        render template: "activities/export"
      end
    end
  end

  def ppg_measures
    @measures = @activity.ppg_measures

    @json = []
    @measures.each do |medidas|
      obj = JSON.parse(medidas['measurement'])
      obj.each do |m|
        @json.append({ppg: m['measure'], timer: m['date']})
      end
    end

    render :json => @json.to_json

  end

  def reprocess
    @activity = Activity.find(params[:id_activity])
    @parsed = JSON.parse(`python3 lib/python/prueba.py #{@activity.id}`)
    @activity.stresses.destroy_all
    @parsed.each do |measurement|
      Stress.create(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id)
    end
    redirect_to dashboard_activity_details_url, notice: "Actividad actualizado."
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_activity
      @activity = Activity.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def activity_params
      params.require(:activity).permit(:name, :start_d, :end_d, :user_id, :id_activity)
    end
end
