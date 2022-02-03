Rails.application.routes.draw do
  resources :measures
  root "welcome#index"
  constraints Rodauth::Rails.authenticated do
    get '/dashboard/index'
    get '/dashboard', to: 'dashboard#index'
    #get '/dashboard/create_patient' => 'dashboard#create_patient'
    get '/dashboard/create_patient' => 'dashboard#create_patient', as: :dashboard_create_patient
    resources :patients
  end

  get '/welcome/index'
  namespace :patient do
    resource :private_api_keys, only: :update
  end

  namespace :api do
    namespace :v1 do
      defaults format: :json do
        resources :patients, only: [:index]
        get 'patients/get_api_key', to: 'patients#get_api_key'
        resources :measures, only: [:index, :create, :show, :update, :destroy]
      end
    end
  end

end
