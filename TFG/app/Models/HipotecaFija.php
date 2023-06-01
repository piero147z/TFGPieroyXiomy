<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class HipotecaFija extends Model
{
    use HasFactory;

    protected $table = 'hipoteca_fija';

    protected $fillable = [
        'banco',
        'TIN',
        'TAE',
    ];
}
