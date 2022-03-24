require "test_helper"

class PpgMeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    @ppg_measure = ppg_measures(:one)
  end

  test "should get index" do
    get ppg_measures_url
    assert_response :success
  end

  test "should get new" do
    get new_ppg_measure_url
    assert_response :success
  end

  test "should create ppg_measure" do
    assert_difference("PpgMeasure.count") do
      post ppg_measures_url, params: { ppg_measure: { activity_id: @ppg_measure.activity_id, measurement: @ppg_measure.measurement } }
    end

    assert_redirected_to ppg_measure_url(PpgMeasure.last)
  end

  test "should show ppg_measure" do
    get ppg_measure_url(@ppg_measure)
    assert_response :success
  end

  test "should get edit" do
    get edit_ppg_measure_url(@ppg_measure)
    assert_response :success
  end

  test "should update ppg_measure" do
    patch ppg_measure_url(@ppg_measure), params: { ppg_measure: { activity_id: @ppg_measure.activity_id, measurement: @ppg_measure.measurement } }
    assert_redirected_to ppg_measure_url(@ppg_measure)
  end

  test "should destroy ppg_measure" do
    assert_difference("PpgMeasure.count", -1) do
      delete ppg_measure_url(@ppg_measure)
    end

    assert_redirected_to ppg_measures_url
  end
end
