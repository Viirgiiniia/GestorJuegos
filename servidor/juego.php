<?php
    /**
     * @Entity @Table(name="juego")
     **/
    class Juego {
        /**
         * @Id
         * @Column(type="integer") @GeneratedValue
         */
        protected $id;
        /**
         * @Column(type="string", length=100, unique=true, nullable=false)
         */
        protected $titulo;
        /**
         * @Column(type="string", length=300, unique=false, nullable=true)
         */
        protected $descripcion;
        /**
         * @Column(type="string", length=50, unique=false, nullable=true)
         */
        protected $imagen;

/**
         * @Column(type="string", length=10, unique=false, nullable=true)
         */
        protected $fecha;

/**
         * @Column(type="integer", unique=false, nullable=true)
         */
        protected $valoracion;

/**
         * @Column(type="string", length=100, unique=false, nullable=true)
         */
        protected $lanzador;

/**
         * @Column(type="integer", length=50, unique=false, nullable=true)
         */
        protected $preinstalado;

        //métodos getter y setter
        public function getId() {
            return $this->id;
        }
        public function setId($id) {
            $this->id = $id;
            return $this;
        }
        
        public function getTitulo() {
            return $this->titulo;
        }
        public function setTitulo($titulo) {
            $this->titulo = $titulo;
            return $this;
        }
        
        public function getDescripcion() {
            return $this->descripcion;
        }
        public function setDescripcion($descripcion) {
            $this->descripcion = $descripcion;
            return $this;
        }
        
        public function getImagen() {
            return $this->imagen;
        }
        public function setImagen($imagen) {
            $this->imagen = $imagen;
            return $this;
        }
        
        public function getFecha() {
            return $this->fecha;
        }
        public function setFecha($fecha) {
            $this->fecha = $fecha;
            return $this;
        }
        
        public function getValoracion() {
            return $this->valoracion;
        }
        public function setValoracion($valoracion) {
            $this->valoracion = $valoracion;
            return $this;
        }
        
        public function getLanzador() {
            return $this->lanzador;
        }
        public function setLanzador($lanzador) {
            $this->lanzador = $lanzador;
            return $this;
        }
        
        public function getPreinstalado() {
            return $this->preinstalado;
        }
        public function setPreinstalado($preinstalado) {
            $this->preinstalado = $preinstalado;
            return $this;
        }
        
    }