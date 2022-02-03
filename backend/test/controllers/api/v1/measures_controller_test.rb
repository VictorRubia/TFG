require "test_helper"

class Api::V1::MeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    @patient_one = Patient.create(name: "#{SecureRandom.hex}", surname: "#{SecureRandom.hex}", username: "#{SecureRandom.hex}@example.com" ,password_hash: "password")
    @patient_two = Patient.create(name: "#{SecureRandom.hex}", surname: "#{SecureRandom.hex}", username: "#{SecureRandom.hex}@example.com" ,password_hash: "password")
    @patient_one_measure = @patient_one.reload.measures.create(hr: (SecureRandom.random_number(9e5) + 1e5).to_i, grades: SecureRandom.random_number(9e5), minutes: SecureRandom.random_number(9e5), seconds: SecureRandom.random_number(9e5), steps: (SecureRandom.random_number(9e5) + 1e5).to_i, accelerometer_X: (SecureRandom.random_number(9e5) + 1e5), accelerometer_Y: (SecureRandom.random_number(9e5) + 1e5), accelerometer_Z: (SecureRandom.random_number(9e5) + 1e5), gyroscope_X: (SecureRandom.random_number(9e5) + 1e5), gyroscope_Y: (SecureRandom.random_number(9e5) + 1e5), gyroscope_Z: (SecureRandom.random_number(9e5) + 1e5))
    @patient_two_measure = @patient_two.reload.measures.create(hr: (SecureRandom.random_number(9e5) + 1e5).to_i, grades: SecureRandom.random_number(9e5), minutes: SecureRandom.random_number(9e5), seconds: SecureRandom.random_number(9e5), steps: (SecureRandom.random_number(9e5) + 1e5).to_i, accelerometer_X: (SecureRandom.random_number(9e5) + 1e5), accelerometer_Y: (SecureRandom.random_number(9e5) + 1e5), accelerometer_Z: (SecureRandom.random_number(9e5) + 1e5), gyroscope_X: (SecureRandom.random_number(9e5) + 1e5), gyroscope_Y: (SecureRandom.random_number(9e5) + 1e5), gyroscope_Z: (SecureRandom.random_number(9e5) + 1e5))
  end

  class Authenticated < Api::V1::MeasuresControllerTest
    test "should get measures" do
      assert_difference("Request.count", 1) do
        get api_v1_measures_path, headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  @patient_one_measure.hr, @response.body
        assert_response :ok
      end
    end

    test "should get measure" do
      assert_difference("Request.count", 1) do
        get api_v1_measure_path(@patient_one_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  @patient_one_measure.hr, @response.body
        assert_response :ok
      end
    end

    test "should handle 404" do
      get api_v1_measure_path("does_not_exis"), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_response :not_found
    end

    test "should create measure" do
      assert_difference(["Measure.count", "Request.count"], 1) do
        measure api_v1_measures_path, headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }, params: { measure: { hr: 55, grades: 60.5 }  }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  "hr", @response.body
        assert_response :created
      end
    end

    # test "should handle errors on create" do
    #   assert_no_difference("Measure.count") do
    #     assert_difference("Request.count", 1) do
    #       measure api_v1_measures_path, headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }, params: { measure: { title: nil, body: nil }  }
    #       assert_equal "application/json; charset=utf-8", @response.content_type
    #       assert_match "message", @response.body
    #       assert_response :unprocessable_entity
    #     end
    #   end
    # end

    test "should update measure" do
      put api_v1_measure_path(@patient_one_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }, params: { measure: { hr: "updated" }  }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_match  "updated", @response.body
      assert_response :ok
    end

    test "should handle errors on update" do
      assert_difference("Request.count", 1) do
        put api_v1_measure_path(@patient_one_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }, params: { measure: { hr: nil }  }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match "message", @response.body
        assert_response :unprocessable_entity
      end
    end

    test "should delete measure" do
      assert_difference(["Measure.count"], -1) do
        assert_difference("Request.count", 1) do
          delete api_v1_measure_path(@patient_one_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
          assert_equal "application/json; charset=utf-8", @response.content_type
          assert_response :ok
        end
      end
    end
  end

  class Unauthorized < Api::V1::MeasuresControllerTest
    test "should not get measures" do
      get api_v1_measures_path
      assert_response :unauthorized
    end

    test "should not load another's measure" do
      get api_v1_measure_path(@patient_two_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_response :unauthorized
    end

    test "should not update another's measure" do
      put api_v1_measure_path(@patient_two_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }, params: { measure: { hr: "updated" }  }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_response :unauthorized
    end

    test "should not delete another's measure" do
      assert_no_difference(["Measure.count", "Request.count"]) do
        delete api_v1_measure_path(@patient_two_measure), headers: { "Authorization": "Token token=#{@patient_one.private_api_key}" }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_response :unauthorized
      end
    end
  end

end
