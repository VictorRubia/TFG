Rails.application.routes.draw do
  constraints Rodauth::Rails.authenticated do
    get "dashboard/index"
  end
  get 'welcome/index'

  root "welcome#index"
end
