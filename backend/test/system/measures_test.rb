require "application_system_test_case"

class MeasuresTest < ApplicationSystemTestCase
  setup do
    @measure = measures(:one)
  end

  test "visiting the index" do
    visit measures_url
    assert_selector "h1", text: "Measures"
  end

  test "should create measure" do
    visit measures_url
    click_on "New measure"

    fill_in "Accelerometer x", with: @measure.accelerometer_X
    fill_in "Accelerometer y", with: @measure.accelerometer_Y
    fill_in "Accelerometer z", with: @measure.accelerometer_Z
    fill_in "Grades", with: @measure.grades
    fill_in "Gyroscope x", with: @measure.gyroscope_X
    fill_in "Gyroscope y", with: @measure.gyroscope_Y
    fill_in "Gyroscope z", with: @measure.gyroscope_Z
    fill_in "Hr", with: @measure.hr
    fill_in "Minutes", with: @measure.minutes
    fill_in "Patient", with: @measure.patient_id
    fill_in "Seconds", with: @measure.seconds
    fill_in "Steps", with: @measure.steps
    click_on "Create Measure"

    assert_text "Measure was successfully created"
    click_on "Back"
  end

  test "should update Measure" do
    visit measure_url(@measure)
    click_on "Edit this measure", match: :first

    fill_in "Accelerometer x", with: @measure.accelerometer_X
    fill_in "Accelerometer y", with: @measure.accelerometer_Y
    fill_in "Accelerometer z", with: @measure.accelerometer_Z
    fill_in "Grades", with: @measure.grades
    fill_in "Gyroscope x", with: @measure.gyroscope_X
    fill_in "Gyroscope y", with: @measure.gyroscope_Y
    fill_in "Gyroscope z", with: @measure.gyroscope_Z
    fill_in "Hr", with: @measure.hr
    fill_in "Minutes", with: @measure.minutes
    fill_in "Patient", with: @measure.patient_id
    fill_in "Seconds", with: @measure.seconds
    fill_in "Steps", with: @measure.steps
    click_on "Update Measure"

    assert_text "Measure was successfully updated"
    click_on "Back"
  end

  test "should destroy Measure" do
    visit measure_url(@measure)
    click_on "Destroy this measure", match: :first

    assert_text "Measure was successfully destroyed"
  end
end
