require "test_helper"

class MeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    @measure = measures(:one)
  end

  test "should get index" do
    get measures_url
    assert_response :success
  end

  test "should get new" do
    get new_measure_url
    assert_response :success
  end

  test "should create measure" do
    assert_difference("Measure.count") do
      post measures_url, params: { measure: { accelerometer_X: @measure.accelerometer_X, accelerometer_Y: @measure.accelerometer_Y, accelerometer_Z: @measure.accelerometer_Z, grades: @measure.grades, gyroscope_X: @measure.gyroscope_X, gyroscope_Y: @measure.gyroscope_Y, gyroscope_Z: @measure.gyroscope_Z, hr: @measure.hr, minutes: @measure.minutes, patient_id: @measure.patient_id, seconds: @measure.seconds, steps: @measure.steps } }
    end

    assert_redirected_to measure_url(Measure.last)
  end

  test "should show measure" do
    get measure_url(@measure)
    assert_response :success
  end

  test "should get edit" do
    get edit_measure_url(@measure)
    assert_response :success
  end

  test "should update measure" do
    patch measure_url(@measure), params: { measure: { accelerometer_X: @measure.accelerometer_X, accelerometer_Y: @measure.accelerometer_Y, accelerometer_Z: @measure.accelerometer_Z, grades: @measure.grades, gyroscope_X: @measure.gyroscope_X, gyroscope_Y: @measure.gyroscope_Y, gyroscope_Z: @measure.gyroscope_Z, hr: @measure.hr, minutes: @measure.minutes, patient_id: @measure.patient_id, seconds: @measure.seconds, steps: @measure.steps } }
    assert_redirected_to measure_url(@measure)
  end

  test "should destroy measure" do
    assert_difference("Measure.count", -1) do
      delete measure_url(@measure)
    end

    assert_redirected_to measures_url
  end
end
