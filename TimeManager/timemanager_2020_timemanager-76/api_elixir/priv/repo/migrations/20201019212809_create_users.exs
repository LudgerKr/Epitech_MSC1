defmodule TimeManager.Repo.Migrations.CreateUsers do
  use Ecto.Migration

  def change do
    create table(:users) do
      add :lastname, :string
      add :firstname, :string
      add :username, :string
      add :email, :string
      add :password_hash, :string
      add :role_id, references(:roles, on_delete: :nothing)
      add :team_id, references(:teams, on_delete: :nothing)

      timestamps()
    end
    create index(:users, [:role_id, :team_id])
    create unique_index(:users, [:email])
    create unique_index(:users, [:username])
  end
end
