<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Formulario extends Model
{
    use HasFactory;

    protected $table = 'formulario';

    protected $fillable = [
        'precio_vivienda',
        'dinero_necesita',
        'años_pago',
        'Tipo_vivienda',
        'Uso_vivienda',
        'provincia',
        'edad',
        'Tipo_banco',
    ];
}
