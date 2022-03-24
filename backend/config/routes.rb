Rails.application.routes.draw do
  root "welcome#index"
  get 'activities/:id/export', to: 'activities#export'
  resources :activities
  constraints Rodauth::Rails.authenticated do
    get '/dashboard/index'
    get '/dashboard', to: 'dashboard#index'
    get '/dashboard/create_user' => 'dashboard#create_user', as: :dashboard_create_user
    post '/dashboard/create_user', to: 'users#create'
    get '/dashboard/activity_user' => 'dashboard#activity_user', as: :dashboard_activity_user
    get '/dashboard/activity_user/:id' => 'dashboard#view_activities', as: :dashboard_view_activity
    get '/dashboard/activity_user/:id/:id_activity' => 'dashboard#activity_details', as: :dashboard_activity_details
    resources :ppg_measures
    resources :users
  end

  get '/welcome/index'
  namespace :user do
    resource :private_api_keys, only: :update
  end

  namespace :api do
    namespace :v1 do
      defaults format: :json do
        resources :users, only: [:index]
        get 'users/get_api_key', to: 'users#get_api_key'
        get 'users/password_recovery', to: 'users#post_password_recovery'
        resources :measures, only: [:index, :create, :show, :update, :destroy]
        resources :activities, only: [:index, :create, :show, :update, :destroy]
        resources :ppg_measures, only: [:index, :create, :show, :update, :destroy]
      end
    end
  end

end
