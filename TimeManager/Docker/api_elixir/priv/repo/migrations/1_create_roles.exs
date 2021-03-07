defmodule TimeManager.Repo.Migrations.CreateRoles do
  use Ecto.Migration

  def change do
    create table(:roles) do
      add :label, :string

      timestamps()
    end
  end
end
