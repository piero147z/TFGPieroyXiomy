<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFormularioTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('formulario', function (Blueprint $table) {
            $table->id();
            $table->integer('precio_vivienda');
            $table->integer('dinero_necesita');
            $table->integer('años_pago');
            $table->string('Tipo_vivienda');
            $table->string('Uso_vivienda');
            $table->string('provincia');
            $table->integer('edad');
            $table->string('Tipo_banco');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('formulario');
    }
}
