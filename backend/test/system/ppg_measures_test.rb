require "application_system_test_case"

class PpgMeasuresTest < ApplicationSystemTestCase
  setup do
    @ppg_measure = ppg_measures(:one)
  end

  test "visiting the index" do
    visit ppg_measures_url
    assert_selector "h1", text: "Ppg measures"
  end

  test "should create ppg measure" do
    visit ppg_measures_url
    click_on "New ppg measure"

    fill_in "Activity", with: @ppg_measure.activity_id
    fill_in "Measurement", with: @ppg_measure.measurement
    click_on "Create Ppg measure"

    assert_text "Ppg measure was successfully created"
    click_on "Back"
  end

  test "should update Ppg measure" do
    visit ppg_measure_url(@ppg_measure)
    click_on "Edit this ppg measure", match: :first

    fill_in "Activity", with: @ppg_measure.activity_id
    fill_in "Measurement", with: @ppg_measure.measurement
    click_on "Update Ppg measure"

    assert_text "Ppg measure was successfully updated"
    click_on "Back"
  end

  test "should destroy Ppg measure" do
    visit ppg_measure_url(@ppg_measure)
    click_on "Destroy this ppg measure", match: :first

    assert_text "Ppg measure was successfully destroyed"
  end
end
